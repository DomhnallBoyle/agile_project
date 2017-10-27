using System;

namespace CSC3045_CS2.Models
{
    public class Account
    {
        public User User { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }

        public Account()
        {

        }

        public Account(User user, String username, String password)
        {
            this.User = user;
            this.Username = username;
            this.Password = password;
        }
        
    }
}
