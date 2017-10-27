namespace CSC3045_CS2.Models
{
    /// <summary>
    /// 
    /// </summary>
    public class User
    {
        public string Forename { get; set; }
        public string Surname { get; set; }
        public string Email { get; set; }
        public Roles Roles { get; set; }

        public User(string forename, string surname, string email, Roles roles)
        {
            this.Forename = forename;
            this.Surname = surname;
            this.Email = email;
            this.Roles = roles;
        }
    }
}
