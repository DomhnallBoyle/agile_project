using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using RestSharp;

namespace CSC3045_CS2.Service
{
    class AuthenticationClient : ServiceClient
    { 
        const string BASE_ENDPOINT = "authentication/";

        public AuthenticationClient() : base () { }

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

        public string Login(Account account)
        {
            var request = new RestRequest(BASE_ENDPOINT + "login", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;

            request.AddBody(account);

            return Execute(request);
        }
    }
}
