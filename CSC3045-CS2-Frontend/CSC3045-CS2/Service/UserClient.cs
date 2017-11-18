using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
namespace CSC3045_CS2.Service
{
    class UserClient : ServiceClient
    {
        const string BASE_ENDPOINT = "user";

        public UserClient() : base() { }

        public User Search(User user)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/search", Method.POST);

            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(user);

            return Execute<User>(request);
        }
    }
}