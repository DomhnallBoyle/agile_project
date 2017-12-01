using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    public partial class BasePage : Page
    {
        public string UserLabel { get; set; }
        public string Image { get; set; }
        public string CurrentPage { get; set; }
        public string HomeImage { get; set; }
        public Permissions Permissions { get; set; }

        public BasePage()
        {
            generateHeader();
        }

        /// <summary>
        /// This Generates a header for Every Page that includes a users name and their profile photo 
        /// </summary>
        public void generateHeader()
        {
            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            Image = Properties.Settings.Default.ProfileImageDirectory + user.ProfilePicture;
            HomeImage = Properties.Settings.Default.ProfileImageDirectory + "waterfallIcon.png";
            Permissions = new Permissions();
        }

        /// <summary>
        /// Generic Log out command
        /// </summary>
        public ICommand LogoutCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (Application.Current.Properties.Contains("user"))
                    {
                        Application.Current.Properties.Remove("user");
                    }

                    Page loginPage = new Login();

                    NavigationService.GetNavigationService(this).Navigate(loginPage);
                });
            }
        }
        public ICommand HomeCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page userDashboard = new UserDashboard();
                    NavigationService.GetNavigationService(this).Navigate(userDashboard);
                });
            }
        }
    }
}
