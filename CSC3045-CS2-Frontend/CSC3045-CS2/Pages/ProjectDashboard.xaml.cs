using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.ComponentModel;
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
        private MenuItem _projectName;
        private ProjectClient _client;

        #endregion

        #region Public Variables

        public Project SelectedProject { get; set; }

        public Permissions Permissions { get; set; }

        public string TitleLabel { get; set; } = "Project Team Members";

        public string UserDashboardButtonLabel { get; set; } = "User Dashboard";

        public string ProductBacklogButtonLabel { get; set; } = "Product Backlog";

        public string ProjectManagerLabel { get; set; } = "Project Manager";

        public string ProjectTeamMembersLabel { get; set; } = "Project Team Members";

        public string ProjectManagerName { get; set; } = "";

        public string ProjectManagerEmail { get; set; } = "";

        public string DeveloperValueLabel { get; set; } = "Developer";

        public string ScrumMasterValueLabel { get; set; } = "Scrum Master";

        public string ProductOwnerValueLabel { get; set; } = "Product Owner";

        public string SetAsButtonText { get; set; } = "Set As";

        public string AddTeamMemberButtonLabel { get; set; } = "Add Team Member";

        #endregion

        public ProjectDashboard(Project selectedProject)
        {
            InitializeComponent();
            DataContext = this;

            _client = new ProjectClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], selectedProject);

            SelectedProject = selectedProject;

            _projects = _client.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
            ProjectDropDownButton.Content = selectedProject.Name;
            AddProjectsToDropdownList();

            ProjectTeamMembers.ItemsSource = _client.GetProjectTeam(SelectedProject.Id);
        }

        #region Class methods

        private void AddProjectsToDropdownList()
        {
            for (int i = 0; i < _projects.Count; i++)
            {
                _projectName = new MenuItem
                {
                    Header = _projects[i].Name,
                    Command = goToCommand,
                    CommandParameter = _projects[i]
                };

                if (this.SelectedProject.Id == _projects[i].Id)
                {
                    _projectName.Background = Brushes.Wheat;
                    _projectName.IsChecked = true;
                }

                menuItems.Items.Add(_projectName);
            }
        }

        public void UpdateProject()
        {
            try
            {
                SelectedProject = _client.UpdateProject(SelectedProject);

                Page ProjectDashboard = new ProjectDashboard(SelectedProject);
                NavigationService.GetNavigationService(this).Navigate(ProjectDashboard);
            }
            catch (RestResponseErrorException ex)
            {
                MessageBox.Show(ex.Message);
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
                    Page productBacklog = new ProductBacklog(SelectedProject);

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

        public ICommand GoToAddTeamMemberCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page addProjectMember = new AddProjectTeamMember(SelectedProject);

                    NavigationService.GetNavigationService(this).Navigate(addProjectMember);
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
                        var newProject = ((Project)param);
                        Page projectDashboard = new ProjectDashboard(newProject);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboard);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }

        public ICommand SetScrumMasterCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    SelectedProject.ScrumMaster = ((User)param);
                    UpdateProject();
                });
            }
        }

        public ICommand SetProductOwnerCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    SelectedProject.ProductOwner = ((User)param);
                    UpdateProject();
                });
            }
        }

        #endregion

    }
}
