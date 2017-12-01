using RestSharp.Deserializers;
using System;

namespace CSC3045_CS2.Models
{
    public class Skill
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "description")]
        public String Description { get; set; }

        public Skill() { }

        public Skill(String description)
        {
            this.Description = description;
        }

    }
}
