using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Windows.Controls;
using System.Windows.Navigation;
using CSC3045_CS2.Models;
using System.Net.Http;
using Newtonsoft.Json;

namespace CSC3045_CS2.Service
{
    class AuthenticationClient : ServiceClient
    { 

        const string BASE_ENDPOINT = "authentication/";
        public AuthenticationClient() : base () { }

        /// <summary>
        /// Creates a request object with register endpoint details.
        /// Adds headers and a serialized account object to the body.
        /// Executes this as a POST request to register with the system.
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        /// <param name="forename"></param>
        /// <param name="surname"></param>
        /// <param name="email"></param>
        /// <param name="isProductOwner"></param>
        /// <param name="isScrumMaster"></param>
        /// <param name="isDeveloper"></param>
        /// <returns>string containing message result from backend</returns>
        public string Register(string username, string password, string forename, string surname, string email, bool isProductOwner, bool isScrumMaster, bool isDeveloper)
        {
            var request = new RestRequest(BASE_ENDPOINT + "register", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;

            Role role = new Role(isProductOwner, isScrumMaster, isDeveloper);
            User user = new User(forename, surname, email, role);
            Account account = new Account(user, username, password);

            request.AddBody(account);

            return Execute(request);
        }
    }
}
