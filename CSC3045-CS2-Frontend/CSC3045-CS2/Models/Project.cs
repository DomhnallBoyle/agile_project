using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    class Project
    {
        public User ProjectManager { get; set; }
        public string ProjectName { get; set; }
        public string Description { get; set; }
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
