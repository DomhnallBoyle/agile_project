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
    class ProjectClient : ServiceClient
    {

        const string BASE_ENDPOINT = "create/";
        public ProjectClient() : base() { }

        public string CreateProject(Project project)
        {
           var request = new RestRequest(BASE_ENDPOINT + "project", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            request.AddBody(project);

            return Execute(request);
        }
    }
}

