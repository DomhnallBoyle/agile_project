using RestSharp.Deserializers;

namespace CSC3045_CS2.Models
{
    /// <summary>
    /// 
    /// </summary>
    public class User
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "forename")]
        public string Forename { get; set; }

        [DeserializeAs(Name = "surname")]
        public string Surname { get; set; }

        [DeserializeAs(Name = "email")]
        public string Email { get; set; }

        [DeserializeAs(Name = "roles")]
        public Roles Roles { get; set; }

        public User() { }

        public User(string forename, string surname, string email, Roles roles)
        {
            this.Forename = forename;
            this.Surname = surname;
            this.Email = email;
            this.Roles = roles;
        }

        public string GetFullName()
        {
            return Forename + " " + Surname;
        }
    }
}
