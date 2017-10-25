namespace CSC3045_CS2.Models
{
    public class User
    {
        public string Forename { get; set; }
        public string Surname { get; set; }
        public string Email { get; set; }

        public User(string forename, string surname, string email)
        {
            this.Forename = forename;
            this.Surname = surname;
            this.Email = email;
        }
       
    }
}
