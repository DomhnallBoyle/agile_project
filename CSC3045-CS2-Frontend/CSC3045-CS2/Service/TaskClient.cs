using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSC3045_CS2.Service
{
    class TaskClient: ServiceClient
    {
        private string BASE_ENDPOINT = "/project/{0}/story/{1}/task";

        public TaskClient() : base()
        {

        }

        public string CreateTask(Task task, long projectId, long userStoryId)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT, projectId, userStoryId), Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(task);

            return Execute(request);
        }
        public List<Task> GetTasksByUserStoryId(long projectId, long userStoryId)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT, projectId, userStoryId), Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<Task>>(request);
        }
    }
}
