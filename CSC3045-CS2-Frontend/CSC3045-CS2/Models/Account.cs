using RestSharp.Deserializers;
using System;

namespace CSC3045_CS2.Models
{
    public class Account
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "user")]
        public User User { get; set; }

        [DeserializeAs(Name = "username")]
        public string Username { get; set; }

        [DeserializeAs(Name = "password")]
        public string Password { get; set; }

        public Account() { }

        public Account(User user, String username, String password)
        {
            this.User = user;
            this.Username = username;
            this.Password = password;
        }

    }
}
