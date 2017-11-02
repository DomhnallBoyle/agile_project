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
        public long ProjectId { get; set; }
        [DeserializeAs(Name = "manager")]
        public User ProjectManager { get; set; }
        [DeserializeAs(Name = "name")]
        public string ProjectName { get; set; }
        [DeserializeAs(Name = "description")]
        public string Description { get; set; }
        [DeserializeAs(Name = "productOwner")]
        public User ProductOwner { get; set; }

        public Project()
        {

        }
        public Project(User projectManager, String projectName, String description, User productOwner)
        {
            this.ProjectManager = projectManager;
            this.ProjectName = projectName;
            this.Description = description;
            this.ProductOwner = productOwner;
        }

        public Project(User projectManager, String projectName, String description)
        {
            this.ProjectManager = projectManager;
            this.ProjectName = projectName;
            this.Description = description;          
        }
    }
}
