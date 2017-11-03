using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;

namespace CSC3045_CS2.Service
{
    class AuthenticationClient : ServiceClient
    { 
        const string BASE_ENDPOINT = "authentication";

        public AuthenticationClient() : base () { }

        /// <summary>
        /// Creates a request object with register endpoint details.
        /// Adds headers and a serialized account object to the body.
        /// Executes this as a POST request to register with the system.
        /// </summary>
        /// <param name="account"></param>
        /// <returns>string containing message result from backend</returns>
        public Account Register(Account account)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/register", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(account);

            return Execute<Account>(request);
        }

        /// <summary>
        /// Sends the account to the backend in an attempt to log in
        /// </summary>
        /// <param name="account">The account to be sent to the backend</param>
        /// <returns>Returns a string with information on the request response</returns>
        public User Login(Account account)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/login", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(account);

            return Execute<User>(request);
        }
    }
}
