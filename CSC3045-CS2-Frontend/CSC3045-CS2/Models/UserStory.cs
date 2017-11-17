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

        [DeserializeAs(Name = "index")]
        public long Index { get; set; }

        [DeserializeAs(Name = "name")]
        public string Name { get; set; }

        [DeserializeAs(Name = "description")]
        public string Description { get; set; }

        [DeserializeAs(Name = "points")]
        public int Points { get; set; }

        [DeserializeAs(Name = "marketValue")]
        public int MarketValue { get; set; }

        [DeserializeAs(Name = "project")]
        public Project Project { get; set; }

        public UserStory() { }

        public UserStory(String name, String description, int marketValue)
        {
            this.Name = name;
            this.Description = description;
            this.MarketValue = marketValue;
        }

        public UserStory(String name, String description, int marketValue, Project project)
        {
            this.Name = name;
            this.Description = description;
            this.MarketValue = marketValue;
            this.Project = project;
        }

    }
}
