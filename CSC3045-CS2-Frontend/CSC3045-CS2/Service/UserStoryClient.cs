﻿using CSC3045_CS2.Models;
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
            var request = new RestRequest("/project/" + userStory.Project.Id + "/story", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(userStory);

            return Execute<UserStory>(request);
        }

        /// <summary>
        /// Gets user story
        /// </summary>
        /// <param name="userStoryId">The ID of the User Story</param>
        /// <returns>The user story, or will throw RestResponseException if error</returns>
        public UserStory GetUserStory(long projectId, long userStoryId)
        {
            var request = new RestRequest("/project/" + projectId + "/story" + "/" + userStoryId, Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<UserStory>(request);
        }

        /// <summary>
        /// Gets user stories for current project
        /// </summary>
        /// <param name="projectId">The ID of the project</param>
        /// <returns>The user stories for the specified project, or will throw RestResponseException if error</returns>
        public List<UserStory> GetUserStories(long projectId)
        {
            var request = new RestRequest("/project/" + projectId + "/story", Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<UserStory>>(request);
        }

        /// <summary>
        /// Gets available user stories for current project
        /// </summary>
        /// <param name="projectId">The ID of the project</param>
        /// <returns>The available user stories for the specified project, or will throw RestResponseException if error</returns>
        public List<UserStory> GetAvailableUserStories(long projectId)
        {
            var request = new RestRequest("/project/" + projectId + "/story/available", Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<UserStory>>(request);
        }

        /// <summary>
        /// Sends PUT request to save order of product backlog for a project
        /// Returns new list of ordered user stories
        /// </summary>
        /// <param name="userStories">New Ordered User Stories</param>
        /// <returns>List of user stories</returns>
        public List<UserStory> SaveOrder(List<UserStory> userStories)
        {
            var request = new RestRequest("/project/" + userStories[0].Project.Id + "/story", Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            request.AddBody(userStories);
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<List<UserStory>>(request);
        }

        /// <summary>
        /// Sends the Acceptance Test to be created to the backend
        /// </summary>
        /// <param name="acceptanceTest">The Acceptance Test to be Created</param>
        /// <returns>The created acceptance Test if successful, or will throw RestResponseException if error</returns>
        public AcceptanceTest CreateAcceptanceTest(AcceptanceTest acceptanceTest)
        {
            var request = new RestRequest("/project/" + acceptanceTest.UserStory.Project.Id + "/story/" + acceptanceTest.UserStory.Id + "/acceptancetest", Method.POST);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(acceptanceTest);

            return Execute<AcceptanceTest>(request);
        }


        /// <summary>
        /// Gets acceptance tests for current user story
        /// </summary>
        /// <param name="acceptanceTest">The Acceptance Test to be Created</param>
        /// <returns>The acceptance tests for the specified user story, or will throw RestResponseException if error</returns>
        public AcceptanceTest UpdateAcceptanceTest(long projectId, long storyId, AcceptanceTest acceptanceTest)
        {
            var request = new RestRequest("/project/" + projectId + "/story/" + storyId + "/acceptancetest/" + acceptanceTest.Id , Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            request.AddBody(acceptanceTest);

            return Execute<AcceptanceTest>(request);
        }


        /// <summary>
        /// Gets acceptanceTests for current story
        /// </summary>
        /// <param name="userStoryID">The ID of the story</param>
        /// <returns>The acceptance tests for the specified user story, or will throw RestResponseException if error</returns>
        public List<AcceptanceTest> GetAcceptanceTestsFromUserStory(long projectId, long userStoryId)
        {
            var request = new RestRequest("/project/" + projectId + "/story/" + userStoryId + "/acceptancetest/", Method.GET);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;

            return Execute<List<AcceptanceTest>>(request);
        }

        /// Updates a particular User Story
        /// </summary>
        /// <param name="userStory">User Story to be updated</param>
        /// <returns>Updated User Story</returns>
        public UserStory UpdateUserStory(long projectId, long userStoryId, UserStory userStory)
        {
            var request = new RestRequest("/project/" + projectId + "/story/" + userStoryId, Method.PUT);
            request.AddHeader("Content-Type", "application/json");
            request.RequestFormat = DataFormat.Json;
            request.AddBody(userStory);
            SimpleJson.CurrentJsonSerializerStrategy = new CamelCaseSerializationStrategy();

            return Execute<UserStory>(request);
        }

    }
}
