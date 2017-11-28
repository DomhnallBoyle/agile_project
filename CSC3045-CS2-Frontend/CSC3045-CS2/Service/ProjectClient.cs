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
using CSC3045_CS2.Utility;

namespace CSC3045_CS2.Service
{
    class ProjectClient : ServiceClient
    {
        const string BASE_ENDPOINT = "project";

        public ProjectClient() : base() { }

        public string CreateProject(Project project)
        {
            var request = new RestRequest(BASE_ENDPOINT, Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(project);

            return Execute(request);
        }

        public List<User> GetProjectTeam(long projectId)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/" + projectId + "/user", Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<User>>(request);
        }

        public Project UpdateProject(Project project)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/" + project.Id, Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(project);

            return Execute<Project>(request);
        }

        public void Add(List<User> users, Project project)
        {
            project.Users = users;
            var request = new RestRequest(BASE_ENDPOINT + "/" + project.Id + "/user", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(project);
            Execute(request);
        }
    }
}

