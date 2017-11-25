﻿using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Service
{
    public class SprintClient : ServiceClient
    {
        const string BASE_ENDPOINT = "sprint";

        public SprintClient() : base() { }

        public Sprint CreateSprint(Sprint sprint)
        {
            var request = new RestRequest(BASE_ENDPOINT, Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(sprint);

            return Execute<Sprint>(request);
        }

        public List<Sprint> GetSprintsInProject(long projectId)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/project/" + projectId, Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<Sprint>>(request);
        }
        public List<User> GetSprintTeam(long sprintId)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/team/" + sprintId, Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<User>>(request);
        }

        public List<User> UpdateSprintTeam(Sprint sprint)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/team", Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(sprint);

            return Execute<List<User>>(request);
        }

        public List<User> GetAvailableDevelopers(long sprintId)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/available/team/" + sprintId, Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<User>>(request);
        }
    }
}