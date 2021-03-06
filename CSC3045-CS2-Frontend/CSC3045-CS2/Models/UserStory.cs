﻿using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using CSC3045_CS2.Utility;
using System.Windows.Input;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    public class UserStory
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

        [DeserializeAs(Name = "sprint")]
        public Sprint Sprint { get; set; }

        [DeserializeAs(Name = "tasks")]
        public List<Task> Tasks { get; set; }

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

        public UserStory(String name, String description, int marketValue, Project project, Sprint sprint)
        {
            this.Name = name;
            this.Description = description;
            this.MarketValue = marketValue;
            this.Project = project;
            this.Sprint = sprint;
        }
    }
}
