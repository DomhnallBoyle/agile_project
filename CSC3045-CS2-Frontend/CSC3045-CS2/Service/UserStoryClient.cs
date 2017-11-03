using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;
using System.Collections.Generic;

namespace CSC3045_CS2.Service
{
    class UserStoryClient : ServiceClient
    {
        const string BASE_ENDPOINT = "story";

        public UserStoryClient() : base() { }

        /// <summary>
        /// Sends the User Story to be created to the backend
        /// </summary>
        /// <param name="userStory">The User Story to be Created</param>
        /// <returns>The created User Story if successful, or will throw RestResponseException if error</returns>
        public UserStory CreateUserStory(UserStory userStory)
        {
            var request = new RestRequest(BASE_ENDPOINT, Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(userStory);

            return Execute<UserStory>(request);
        }

        /// <summary>
        /// Gets user stories for current project
        /// </summary>
        /// <param name="projectId">The ID of the project</param>
        /// <returns>The user stories for the specified project, or will throw RestResponseException if error</returns>
        public List<UserStory> GetUserStories(long projectId)
        {
            var request = new RestRequest(BASE_ENDPOINT + "/project/" + projectId, Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<UserStory>>(request);
        }

    }
}
