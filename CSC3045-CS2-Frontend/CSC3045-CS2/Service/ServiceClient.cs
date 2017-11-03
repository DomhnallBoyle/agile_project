using CSC3045_CS2.Exception;
using RestSharp;
using RestSharp.Deserializers;
using System.Collections.Generic;
using System.Linq;
using System.Net;

namespace CSC3045_CS2.Service
{
    public class ServiceClient
    {
        protected const string BASE_URL = "http://localhost:8000";

        private RestClient _client;
        private JsonDeserializer _deserializer;

        public ServiceClient()
        {
            _client = new RestClient
            {
                BaseUrl = new System.Uri(BASE_URL)
            };
            _deserializer = new JsonDeserializer();
        }

        /// <summary>
        /// Executes a request where data is expected in the response body.
        /// </summary>
        /// <typeparam name="T">The model expected in the response body.</typeparam>
        /// <param name="request">The RestRequest object containing the call data.</param>
        /// <returns>Returns the deserialized/mapped data in the response body in case of a successful (response code 2XX, eg 200) REST call.</returns>
        /// <exception cref="RestResponseErrorException">Thrown if there is an error response (response code 4XX, eg 404) and bubbled up to be handled by UI</exception>
        protected T Execute<T>(RestRequest request) where T : new()
        {
            if (Properties.Settings.Default.AuthToken != null)
            {
                request.AddHeader("Authorization", Properties.Settings.Default.AuthToken);
            }
            var response = this._client.Execute(request);
            if (response.ResponseStatus != ResponseStatus.Completed)
            {
                throw new RestResponseErrorException("Failed getting response from server");
            }
            if (!IsSuccessfulStatusCode(response.StatusCode))
            {
                throw new RestResponseErrorException(response.Content, response.StatusCode);
            }
            CheckForAuthToken(response.Headers);

            return _deserializer.Deserialize<T>(response);
        }

        /// <summary>
        /// Executes a request where no data is expected in the response body.
        /// </summary>
        /// <param name="request">The RestRequest object containing the call data.</param>
        /// <returns>The body of the response as a raw string. Can be ignored, only needed a custom response message must be sent back.</returns>
        /// <exception cref="RestResponseErrorException">Thrown if there is an error response (response code 4XX, eg 404) and bubbled up to be handled by UI</exception>
        protected string Execute(RestRequest request)
        {
            if (Properties.Settings.Default.AuthToken != null)
            {
                request.AddHeader("Authorization", Properties.Settings.Default.AuthToken);
            }

            var response = this._client.Execute(request);
            if (response.ResponseStatus != ResponseStatus.Completed)
            {
                throw new RestResponseErrorException("Failed getting response from server");
            }
            if (!IsSuccessfulStatusCode(response.StatusCode))
            {
                throw new RestResponseErrorException(response.Content, response.StatusCode);
            }
            CheckForAuthToken(response.Headers);

            return response.Content;
        }

        /// <summary>
        /// Checks a given HTTP StatusCode to see if it was a Success or an Error
        /// </summary>
        /// <param name="statusCode">The HTTP Status Code to be checked</param>
        /// <returns>A boolean, true for Successful, false for Error</returns>
        private bool IsSuccessfulStatusCode(HttpStatusCode statusCode)
        {
            return (int)statusCode >= 200 && (int)statusCode <= 399;
        }

        /// <summary>
        /// Checks for auth token in response headers
        /// Sets the settings property if auth token is not null
        /// </summary>
        /// <param name="headers">List of response headers</param>
        private void CheckForAuthToken(IList<Parameter> headers)
        {
            Parameter authToken = headers.ToList().Find(x => x.Name == "Authorization");
            if (authToken != null)
            {
                Properties.Settings.Default.AuthToken = authToken.Value.ToString();
            }
        }
    }
}
