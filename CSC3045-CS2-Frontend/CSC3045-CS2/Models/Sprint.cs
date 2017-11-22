using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    public class Sprint
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "startDate")]
        public DateTime StartDate { get; set; }

        [DeserializeAs(Name = "endDate")]
        public DateTime EndDate { get; set; }

        [DeserializeAs(Name = "project")]
        public Project Project { get; set; }

        [DeserializeAs(Name = "scrumMaster")]
        public User ScrumMaster { get; set; }

        [DeserializeAs(Name = "users")]
        public List<User> users { get; set; }

        public Sprint() { }

        public Sprint(DateTime startDate, DateTime endDate, Project project, User scrumMaster)
        {
            this.StartDate = startDate;
            this.EndDate = endDate;
            this.Project = project;
            this.ScrumMaster = scrumMaster;
        }
    }
}
