using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    class UserStory
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "name")]
        public string Name { get; set; }

        [DeserializeAs(Name = "description")]
        public string Description { get; set; }

        [DeserializeAs(Name = "storyPoints")]
        public int StoryPoints { get; set; }

        [DeserializeAs(Name = "marketValue")]
        public int MarketValue { get; set; }

        [DeserializeAs(Name = "assigned")]
        public bool Assigned { get; set; }

        //TODO: Below Doesn't exist yet on branch
        //[DeserializeAs(Name = "project")]
        //public Project Project { get; set; }

        // Associated Tasks - Sprint 2 

        // Acceptance Tests - Sprint 2

        public UserStory() { }

        public UserStory(String name, String description, int storyPoints, int marketValue)
        {
            this.Name = name;
            this.Description = description;
            this.StoryPoints = storyPoints;
            this.MarketValue = marketValue;
        }

        /*
        public UserStory(String name, String description, int storyPoints, int marketValue, Project project)
        {
            this.Name = name;
            this.Description = description;
            this.StoryPoints = storyPoints;
            this.MarketValue = marketValue;
            this.Project = project;
        }
        */
    }
}
