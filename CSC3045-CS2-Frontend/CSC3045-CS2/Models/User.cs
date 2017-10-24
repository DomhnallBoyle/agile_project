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
        public Role Role { get; set; }

        public User(string forename, string surname, string email, Role role)
        {
            this.forename = forename;
            this.surname = surname;
            this.email = email;
            this.Role = role;
        }
       
    }
}
