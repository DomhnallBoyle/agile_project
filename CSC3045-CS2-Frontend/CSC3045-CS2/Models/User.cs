using RestSharp.Deserializers;
using System.Collections.Generic;

namespace CSC3045_CS2.Models
{
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

        [DeserializeAs(Name = "profilePicture")]
        public string ProfilePicture { get; set; }

        [DeserializeAs(Name = "roles")]
        public Roles Roles { get; set; }

        [DeserializeAs(Name = "projects")]
        public List<Project> Projects { get; set; }

        [DeserializeAs(Name = "sprints")]
        public List<Sprint> Sprints { get; set; }

        [DeserializeAs(Name = "skills")]
        public List<Skill> Skills { get; set; }

        public User() { }

        public User(long id)
        {
            this.Id = id;
        }

        public User(string forename, string surname, string email, Roles roles)
        {
            this.Forename = forename;
            this.Surname = surname;
            this.Email = email;
            this.Roles = roles;
        }

        public User(string forename, string surname, string email, string profilePicture, Roles roles, List<Skill> skills)
        {
            this.Forename = forename;
            this.Surname = surname;
            this.Email = email;
            this.ProfilePicture = profilePicture;
            this.Roles = roles;
            this.Skills = skills;
        }

        public User(string forename, string surname, string email, string profilePicture, Roles roles)
        {
            this.Forename = forename;
            this.Surname = surname;
            this.Email = email;
            this.Roles = roles;
            this.ProfilePicture = profilePicture;
        }

        public string GetFullName()
        {
            return this.Forename + " " + this.Surname;
        }
    }
}
