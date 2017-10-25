using CSC3045_CS2.Exception;
using RestSharp;
using RestSharp.Authenticators;
using RestSharp.Deserializers;
using System.Net;
using System.Windows.Controls;
using System.Windows.Navigation;
using CSC3045_CS2.Pages;
using System;

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
            var response = this._client.Execute(request);

            if (response.ResponseStatus != ResponseStatus.Completed)
            {
                Console.WriteLine("Failed getting response from server");
                throw new RestResponseErrorException("Failed getting response from server");
            }

            if (!IsSuccessfulStatusCode(response.StatusCode))
            {
                Console.WriteLine("Error " + response.StatusCode + " from server: " + response.Content);
                throw new RestResponseErrorException(response.Content, response.StatusCode);
            }

            return _deserializer.Deserialize<T>(response);
        }

        /// <summary>
        /// Executes a request where no data is expected in the response body.
        /// </summary>
        /// <param name="request">The RestRequest object containing the call data.</param>
        /// <returns>The body of the response as a raw string. Can be ignored, only needed a custom response message must be sent back.</returns>
        /// <exception cref="RestResponseErrorException">Thrown if there is an error response (response code 4XX, eg 404) and bubbled up to be handled by UI</exception>
        public string Execute(RestRequest request)
        {
            var response = this._client.Execute(request);

            if (response.ResponseStatus != ResponseStatus.Completed)
            {
                Console.WriteLine("Failed getting response from server");
                throw new RestResponseErrorException("Failed getting response from server");
            }

            if (!IsSuccessfulStatusCode(response.StatusCode))
            {
                Console.WriteLine("Error " + response.StatusCode + " from server: " + response.Content);
                throw new RestResponseErrorException(response.Content, response.StatusCode);
            }

            return response.Content;
        }

        private bool IsSuccessfulStatusCode(HttpStatusCode statusCode)
        {
            return (int) statusCode >= 200 && (int) statusCode <= 399;
        }
    }
}
