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

        public string Register(string username, string password, string forename, string surname, string email)
        {
            var request = new RestRequest(BASE_ENDPOINT+"register", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            User user = new User(forename, surname, email);
            Account account = new Account(user, username, password);
            request.AddBody(account);
            
            return Execute(request);
        }
    }
}
