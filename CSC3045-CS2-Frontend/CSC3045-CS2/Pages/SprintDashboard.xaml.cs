using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for SprintDashboard.xaml
    /// </summary>
    public partial class SprintDashboard : BasePage
    {
        #region Private variables

        public SprintClient _client;

        #endregion

        #region Public variables

        public Sprint CurrentSprint { get; set; }
        public Permissions Permissions { get; set; }

        #endregion

        public SprintDashboard(Sprint sprint, bool fromFile)
        {
            InitializeComponent();
            CurrentPage = this.Title;

            DataContext = this;

            _client = new SprintClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], sprint.Project);

            if (!fromFile)
            {
                sprint.Users = _client.GetSprintTeam(sprint.Project.Id, sprint.Id);
            }

            CurrentSprint = sprint;
        }

        #region Command and Event methods
      

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
