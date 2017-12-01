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

        /// <summary>
        /// Method for sending request to create a task
        /// </summary>
        /// <param name="task">Task object</param>
        /// <param name="projectId">Project Id the userstory belongs to</param>
        /// <param name="userStoryId">UserStory id the task belongs to</param>
        /// <returns></returns>
        public Task CreateTask(Task task, long projectId, long userStoryId)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT, projectId, userStoryId), Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(task);

            return Execute<Task>(request);
        }

        /// <summary>
        /// Returns tasks by user story id
        /// </summary>
        /// <param name="projectId">Project Id the userstory belongs to</param>
        /// <param name="userStoryId">UserStory id the task belongs to</param>
        /// <returns></returns>
        public List<Task> GetTasksByUserStoryId(long projectId, long userStoryId)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT, projectId, userStoryId), Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<Task>>(request);
        }

        /// <summary>
        /// Updates a given task
        /// </summary>
        /// <param name="projectId">Project Id the userstory belongs to</param>
        /// <param name="userStoryId">UserStory id the task belongs to</param>
        /// <param name="task">Task object</param>
        /// <returns></returns>
        public Task UpdateTask(long projectId, long userStoryId,Task task)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT + "/" + task.Id, projectId, userStoryId), Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();
            request.AddBody(task);
            return Execute<Task>(request);
        }

        public List<TaskEstimate> GetTaskEstimates(long projectId, long userStoryId, long taskId)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT + "/" + taskId, projectId, userStoryId), Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<TaskEstimate>>(request);
        }

        public List<TaskEstimate> UpdateTaskEstimates(long projectId, long userStoryId, long taskId, List<TaskEstimate> taskEstimates)
        {
            var request = new RestRequest(string.Format(BASE_ENDPOINT + "/" + taskId, projectId, userStoryId), Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();
            request.AddBody(taskEstimates);

            return Execute<List<TaskEstimate>>(request);
        }
    }
}
