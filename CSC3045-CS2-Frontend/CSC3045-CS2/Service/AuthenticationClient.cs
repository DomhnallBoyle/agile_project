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
        /// <param name="account"></param>
        /// <returns>string containing message result from backend</returns>
        public string Register(Account account)
        {
            var request = new RestRequest(BASE_ENDPOINT + "register", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;

            request.AddBody(account);

            return Execute(request);
        }
    }
}
