using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    public class Task
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "name")]
        public string Name { get; set; }

        [DeserializeAs(Name = "description")]
        public string Description { get; set; }

        [DeserializeAs(Name = "initialEstimate")]
        public int InitialEstimate { get; set; }

        [DeserializeAs(Name = "userStory")]
        public UserStory UserStory { get; set; }

        [DeserializeAs(Name = "assignee")]
        public User Assignee { get; set; }

        public Task() { }

        public Task(string name, string description, int initialEstimate, UserStory userStory, User assignee)
        {
            this.Name = name;
            this.Description = description;
            this.InitialEstimate = initialEstimate;
            this.UserStory = userStory;
            this.Assignee = assignee;
        }
    }
}
