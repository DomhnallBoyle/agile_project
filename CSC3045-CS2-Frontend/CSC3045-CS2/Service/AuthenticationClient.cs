using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;
using System;

namespace CSC3045_CS2.Service
{
    class AuthenticationClient : ServiceClient
    { 
        const string BASE_ENDPOINT = "authentication/";

        public AuthenticationClient() : base () { }

        /// <summary>
        /// Builds the User & Account and sends them to the backend in an attempt to register them
        /// </summary>
        /// <param name="username">The username of the Account</param>
        /// <param name="password">The password of the Account</param>
        /// <param name="forename">The forename of the User</param>
        /// <param name="surname">The surname of the User</param>
        /// <param name="email">The email of the User</param>
        /// <returns>Returns a string with information on the request response</returns>
        public string Register(string username, string password, string forename, string surname, string email)
        {
            var request = new RestRequest(BASE_ENDPOINT + "register", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;

            User user = new User(forename, surname, email);
            Account account = new Account(user, username, password);

            request.AddBody(account);
            
            return Execute(request);
        }

        /// <summary>
        /// Sends the account to the backend in an attempt to log in
        /// </summary>
        /// <param name="account">The account to be sent to the backend</param>
        /// <returns>Returns a string with information on the request response</returns>
        public string Login(Account account)
        {
            var request = new RestRequest(BASE_ENDPOINT + "login", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(account);

            return Execute(request);
        }
    }
}
