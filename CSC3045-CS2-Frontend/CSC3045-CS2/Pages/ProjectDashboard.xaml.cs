using CSC3045_CS2.Exception;
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
    /// Interaction logic for ProjectDashboard.xaml
    /// </summary>
    public partial class ProjectDashboard : Page
    {
        #region Private Variables

        private List<Project> _projects;
        private List<User> _user;
        private int _currentProjectNumber;
        private MenuItem _projectName;
        private ProjectClient _client;

        #endregion

        #region Public Variables
        User currentUser = (User)Application.Current.Properties["user"];

        public string TitleLabel { get; set; } = "Project Team Members";

        public string UserDashboardButtonLabel { get; set; } = "User Dashboard";

        public string ProductBacklogButtonLabel { get; set; } = "Product Backlog";

        public string ProjectManagerLabel { get; set; } = "Project Manager";

        public string ProjectTeamMembersLabel { get; set; } = "Project Team Members";

        public String setProductManagerName { get; set; } = "";

        public String setProductManagerEmail { get; set; } = "";

        public String DeveloperValueLabel { get; set; } = "Developer";

        public String ScrumMasterValueLabel { get; set; } = "Scrum Master";

        public String ProductOwnerValueLabel { get; set; } = "Product Owner";

        #endregion

        public ProjectDashboard(List<Project> projects, int currentProjectNumber)
        {
            InitializeComponent();
            DataContext = this;
            this._projects = projects;
            this._currentProjectNumber = currentProjectNumber;
            ProjectDropDownButton.Content = this._projects[currentProjectNumber].ProjectName;

            Project project = this._projects[currentProjectNumber];
            

            addProjectstoList();
            pageSetup();
                _user = new List<User>();
                Roles role = new Roles(false, false, true);
                Roles role1 = new Roles(false, true, true);
                _user.Add(new User("Zoey", "Longridge", "zoey@hotmail.com", role));
                _user.Add(new User("Kevin", "Martin", "zoey@hotmail.com", role1));
                _user.Add(new User("Darren", "huehuehue", "hue@hotmail.com", role));
                ProjectTeamMembers.ItemsSource = _user;
 

           // ProjectTeamMembers.ItemsSource = _client.GetProjectTeam(project.ProjectId);

        }

        #region Class methods

        public void pageSetup()
        {
            setProductManagerName = currentUser.Forename + " " + currentUser.Surname;
            setProductManagerEmail = currentUser.Email;
        }

        private void addProjectstoList()
        {
            for (int i = 0; i < _projects.Count; i++)
            {
                _projectName = new MenuItem
                {
                    Header = _projects[i].ProjectName,
                    Command = goToCommand,
                    CommandParameter = i
                };

                if (this._currentProjectNumber == i)
                {
                    _projectName.Background = Brushes.Wheat;
                    _projectName.IsChecked = true;
                }

                menuItems.Items.Add(_projectName);
            }
        }

        #endregion

        #region Command methods

        public ICommand ProjectDropDown
        {
            get
            {
                return new RelayCommand(param =>
                {
                    ProjectDropDownButton.ContextMenu.IsEnabled = true;
                    ProjectDropDownButton.ContextMenu.PlacementTarget = ProjectDropDownButton;
                    ProjectDropDownButton.ContextMenu.Placement = System.Windows.Controls.Primitives.PlacementMode.Bottom;
                    ProjectDropDownButton.ContextMenu.IsOpen = true;
                });
            }
        }

        public ICommand GoToProductBacklogCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page productBacklog = new ProductBacklog();

                    NavigationService.GetNavigationService(this).Navigate(productBacklog);
                });
            }
        }

        public ICommand GoToUserDashboardCommand
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

        public ICommand goToCommand

        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        var projectNumber = ((int)param);
                        Page projectDashboard = new ProjectDashboard(_projects, projectNumber);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboard);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }

        #endregion
    }
}
