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
    /// Interaction logic for UserDashboard.xaml
    /// </summary>
    public partial class UserDashboard : Page
    {
        #region Private variables

        private List<Project> _projectList;
        private TextBlock _projectName, _projectDescription, _projectRoles;
        private Button _toProjectDashboard;
        private ProjectClient _client;

        #endregion

        #region Public variables

        public String UserLabel { get; set; }

        public String LogoutButtonLabel { get; set; } = "Logout";

        #endregion

        public UserDashboard()
        {
            InitializeComponent();
            DataContext = this;

            UserLabel = ((User)Application.Current.Properties["user"]).GetFullName();

            _client = new ProjectClient();

            GetProjects();
        }

        #region Class methods

        private void AddProjectsToUIList()
        {
            for (int i = 0; i < _projectList.Count; i++)
            {
                _projectName = new TextBlock
                {
                    Text = _projectList[i].Name
                };
                _projectDescription = new TextBlock
                {
                    Text = _projectList[i].Description
                };
                _projectRoles = new TextBlock
                {
                    Text = new Permissions((User)Application.Current.Properties["user"], _projectList[i]).getPermissionsAsString()
                };
                _toProjectDashboard = new Button
                {
                    Content = "Go to Project",
                    Command = GoToCommand,
                    CommandParameter = _projectList[i]
                };

                ProjectNamePanel.Children.Add(_projectName);
                DetailsPanel.Children.Add(_projectDescription);
                RolesPanel.Children.Add(_projectRoles);
                ButtonPanel.Children.Add(_toProjectDashboard);
            }
        }

        private void GetProjects()
        {
            try
            {
                _projectList = _client.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
                AddProjectsToUIList();
            }
            catch (RestResponseErrorException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        #endregion

        #region Command methods

        public ICommand GoToCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        var selectedProject = (Project)param;
                        Page projectDashboardPage = new ProjectDashboard(selectedProject);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }


        public ICommand NavigateToCreateProjectCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createProjectPage = new CreateProject();

                    NavigationService.GetNavigationService(this).Navigate(createProjectPage);
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

        #endregion
    }
}
