using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
   

    public class Account
    {
        public User user { get; set; }
        public string username { get; set; }
        public string password { get; set; }

        public Account()
        {

        }
        public Account(User user, String username, String password)
        {
            this.user = user;
            this.username = username;
            this.password = password;
        }
        
    }
}
