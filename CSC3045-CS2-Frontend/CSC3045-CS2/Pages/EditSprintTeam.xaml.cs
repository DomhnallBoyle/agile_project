using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
    /// Interaction logic for AddSprintTeamMember.xaml
    /// </summary>
    public partial class EditSprintTeam : Page
    {
        #region Private variables

        private SprintClient _sprintClient;

        #endregion

        #region Public variables

        public Sprint CurrentSprint { get; set; }

        public ObservableCollection<User> SprintTeam { get; set; }

        public ObservableCollection<User> AvailableDevelopers { get; set; }

        public string UserLabel { get; set; }

        public string image { get; set; }

        public string CurrentPage { get; set; }

        #endregion

        public EditSprintTeam(Sprint sprint)
        {
            InitializeComponent();
            generateHeader();
            DataContext = this;

            _sprintClient = new SprintClient();

            CurrentSprint = sprint;
            SprintTeam = new ObservableCollection<User>(sprint.Users);

            try
            {
                AvailableDevelopers = new ObservableCollection<User>(_sprintClient.GetAvailableDevelopers(sprint.Id));
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowInfoBox(ex.Message);
            }
        }

        #region Command methods

        public ICommand SaveTeamCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        List<User> teamMembers = SprintTeam.ToList<User>();
                        CurrentSprint.Users = teamMembers;

                        _sprintClient.UpdateSprintTeam(CurrentSprint);

                        MessageBoxUtil.ShowSuccessBox("Team saved");

                        Page sprintDashboardPage = new SprintDashboard(CurrentSprint, false);

                        NavigationService.GetNavigationService(this).Navigate(sprintDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
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
        public void generateHeader()
        {
            User user = ((User)Application.Current.Properties["user"]);
            UserLabel = user.GetFullName();
            image = Properties.Settings.Default.ProfileImageDirectory + user.ProfilePicture;
            CurrentPage = this.Title;
        }

        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        Page sprintDashboardPage = new SprintDashboard(CurrentSprint, false);

                        NavigationService.GetNavigationService(this).Navigate(sprintDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        #endregion
    }
}
