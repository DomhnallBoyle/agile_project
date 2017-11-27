using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for SprintDashboard.xaml
    /// </summary>
    public partial class SprintDashboard : Page
    {
        #region Private variables

        public SprintClient _client;

        #endregion

        #region Public variables

        public Sprint CurrentSprint { get; set; }
        public Permissions Permissions { get; set; }
        public string UserLabel { get; set; }

        public string image { get; set; }

        public string CurrentPage { get; set; }

        #endregion

        public SprintDashboard(Sprint sprint, bool fromFile)
        {
            InitializeComponent();
            generateHeader();

            DataContext = this;

            _client = new SprintClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], sprint.Project);

            if (!fromFile)
            {
                sprint.Users = _client.GetSprintTeam(sprint.Id);
            }

            CurrentSprint = sprint;
        }

        #region Command and Event methods
        public void generateHeader()
        {
            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            image = Properties.Settings.Default.ProfileImageDirectory + user.ProfilePicture;
            CurrentPage = this.Title;
        }
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

        public ICommand NavigateToManageSprintsCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page editSprintTeam = new EditSprintTeam(CurrentSprint);

                    NavigationService.GetNavigationService(this).Navigate(editSprintTeam);
                });
            }
        }

        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page manageSprintsPage = new ManageSprints(CurrentSprint.Project);

                    NavigationService.GetNavigationService(this).Navigate(manageSprintsPage);
                });
            }
        }

        #endregion
    }
}
