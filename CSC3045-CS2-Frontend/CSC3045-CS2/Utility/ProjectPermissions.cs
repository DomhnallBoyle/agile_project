using CSC3045_CS2.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Utility
{
    public class ProjectPermissions
    {
        public Project Project { get; set; }
        public Permissions Permissions { get; set; }

        public ProjectPermissions(Project project, User user)
        {
            Project = project;
            Permissions = new Permissions(user, project);
        }
    }
}
