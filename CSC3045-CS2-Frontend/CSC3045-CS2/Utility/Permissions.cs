﻿using CSC3045_CS2.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Utility
{
    public class Permissions
    {
        public bool Developer { get; set; }

        public bool ScrumMaster { get; set; }

        public bool ProductOwner { get; set; }

        public bool Manager { get; set; }

        public Permissions(User user, Project project)
        {
            User userInProject = project.Users.Find(userOnProject => user.Id == userOnProject.Id);
            this.Developer = user.Roles.Developer == true && userInProject != null;

            this.ScrumMaster = false;
            if (project.ScrumMasters != null)
            {
                foreach (User scrumMaster in project.ScrumMasters)
                {
                    if (scrumMaster.Id == user.Id)
                    {
                        this.ScrumMaster = true;
                        break;
                    }
                }
            }

            this.ProductOwner = project.ProductOwner != null && project.ProductOwner.Id == user.Id ? true : false;
            this.Manager = project.Manager.Id == user.Id ? true : false;
        }
        
        public Permissions()
        {
            this.Manager = false;
            this.Developer = false;
            this.ScrumMaster = false;
            this.ProductOwner = false;
        }
    }
}
