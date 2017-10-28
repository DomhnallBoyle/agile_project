using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    class Project
    {
        public User projectManager { get; set; }
        public string projectName { get; set; }
        public string description { get; set; }
        public User productOwner { get; set; }

        public Project()
        {

        }
        public Project(User projectManager, String projectName, String description, User productOwner)
        {
            this.projectManager = projectManager;
            this.projectName = projectName;
            this.description = description;
            this.productOwner = productOwner;
        }
    }
}
