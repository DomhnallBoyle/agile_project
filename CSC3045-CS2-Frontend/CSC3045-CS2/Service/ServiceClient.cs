using CSC3045_CS2.Exception;
using RestSharp;
using RestSharp.Authenticators;
using RestSharp.Deserializers;
using System.Net;
using System.Windows.Controls;
using System.Windows.Navigation;
using CSC3045_CS2.Pages;
namespace CSC3045_CS2.Service
{
   public class ServiceClient
    {
        const string BASE_URL = "http://localhost:8000";

    
        private RestClient client;
        private JsonDeserializer deserializer;

        public ServiceClient()
        {
            

            client = new RestClient
            {
                BaseUrl = new System.Uri(BASE_URL),
                //Authenticator = new HttpBasicAuthenticator()
            };
            deserializer = new JsonDeserializer();
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
            var response = this.client.Execute(request);

            if (response.ErrorException != null)
            {
                throw new RestResponseErrorException(response.Content);
            }

            return deserializer.Deserialize<T>(response);
        }

        /// <summary>
        /// Executes a request where no data is expected in the response body.
        /// </summary>
        /// <param name="request">The RestRequest object containing the call data.</param>
        /// <returns>The body of the response as a raw string. Can be ignored, only needed a custom response message must be sent back.</returns>
        /// <exception cref="RestResponseErrorException">Thrown if there is an error response (response code 4XX, eg 404) and bubbled up to be handled by UI</exception>
        public string Execute(RestRequest request)
        {
            var response = this.client.Execute(request);
            var statCode = "";

            if (response.ErrorException != null)
            {
                throw new RestResponseErrorException(response.Content);
            }
            if(response.StatusCode == HttpStatusCode.NotFound)
            {
                
                statCode = response.StatusCode.ToString();
                
                return "Error 404: Page Does Not Exist";
            }
            if (response.StatusCode == HttpStatusCode.Conflict)
            {
                return "This Username has already been Used, Please Select Another";
            }
            if(response.StatusCode == HttpStatusCode.OK)
            {
                return "Succesfully Registered";
                
            }
            return "CPD";
        }
        
    }
}
