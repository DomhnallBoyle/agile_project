using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using RestSharp;

namespace CSC3045_CS2.Service
{
    class UserStoryClient : ServiceClient
    {
        const string BASE_ENDPOINT = "story/";

        public UserStoryClient() : base() { }

        /// <summary>
        /// Sends the User Story to be created to the backend
        /// </summary>
        /// <param name="userStory">The User Story to be Created</param>
        /// <returns>The created User Story if successful, or will throw RestResponseException if error</returns>
        public UserStory CreateUserStory(UserStory userStory)
        {
            var request = new RestRequest(BASE_ENDPOINT + "create", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(userStory);

            return Execute<UserStory>(request);
        }

    }
}
