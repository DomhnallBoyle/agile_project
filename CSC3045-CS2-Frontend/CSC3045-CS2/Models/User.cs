using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    /// <summary>
    /// 
    /// </summary>
    public class User
    {
        public string forename { get; set; }
        public string surname { get; set; }
        public string email { get; set; }
        public Roles Roles { get; set; }

        public User(string forename, string surname, string email, Roles roles)
        {
            this.forename = forename;
            this.surname = surname;
            this.email = email;
            this.Roles = roles;
        }
       
    }
}
