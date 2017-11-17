using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    public class Project
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }
        [DeserializeAs(Name = "manager")]
        public User Manager { get; set; }
        [DeserializeAs(Name = "name")]
        public string Name { get; set; }
        [DeserializeAs(Name = "description")]
        public string Description { get; set; }
        [DeserializeAs(Name = "productOwner")]
        public User ProductOwner { get; set; }
        [DeserializeAs(Name = "scrumMasters")]
        public List<User> ScrumMasters { get; set; }
        [DeserializeAs(Name = "users")]
        public List<User> Users { get; set; }

        public Project()
        {

        }
        public Project(User projectManager, String projectName, String description, User productOwner, List<User> scrumMasters)
        {
            this.Manager = projectManager;
            this.Name = projectName;
            this.Description = description;
            this.ProductOwner = productOwner;
            this.ScrumMasters = scrumMasters;
        }

        public Project(User projectManager, String projectName, String description)
        {
            this.Manager = projectManager;
            this.Name = projectName;
            this.Description = description;
        }
    }
}
