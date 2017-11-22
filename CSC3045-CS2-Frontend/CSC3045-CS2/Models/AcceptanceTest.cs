using RestSharp.Deserializers;
using System;

namespace CSC3045_CS2.Models
{
    class AcceptanceTest
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "given")]
        public string Given { get; set; }

        [DeserializeAs(Name = "when")]
        public string When { get; set; }

        [DeserializeAs(Name = "then")]
        public string Then { get; set; }

        [DeserializeAs(Name = "userStory")]
        public UserStory UserStory { get; set; }

        [DeserializeAs(Name = "completed")]
        public Boolean Completed { get; set; }


        public AcceptanceTest() { }

        public AcceptanceTest(String given, String when, String then,UserStory userStory, Boolean Completed)
        {
            this.Given = given;
            this.When = when;
            this.Then = then;
            this.UserStory = userStory;
            this.Completed = Completed;

        }

        public AcceptanceTest(String given, String when, String then, UserStory userStory)
        {
            this.Given = given;
            this.When = when;
            this.Then = then;
            this.UserStory = userStory;
            this.Completed = false;
        }
    }
}

