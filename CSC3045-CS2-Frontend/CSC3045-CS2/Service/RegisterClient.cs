using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Windows.Controls;
using System.Windows.Navigation;
using CSC3045_CS2.Pages;

namespace CSC3045_CS2.Service
{
    class RegisterClient : ServiceClient
    { 

        const string BASE_ENDPOINT = "Register";
        public RegisterClient() : base () { }

        public string Register(string username, string password, string firstname, string surname, string email)
        {
            var request = new RestRequest(BASE_ENDPOINT, Method.POST);

                request.AddParameter("username", username);
                request.AddParameter("password", password);
                request.AddParameter("firstname", firstname);
                request.AddParameter("surname", surname);
                request.AddParameter("email", email);

           
                return Execute(request);
              

           


         
           
        }
}
}
