using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;

using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for ProjectDashboard.xaml
    /// </summary>
    public partial class ProjectDashboard : Page, INotifyPropertyChanged
    {
        #region Private Variables

        private List<Project> _projects;

        private MenuItem _projectMenu;

        private ProjectClient _client;

        private Project _selectedProject;

        #endregion

        #region Public Variables

        public Project SelectedProject
        {
            get { return _selectedProject; }
            set
            {
                _selectedProject = value;
                OnPropertyChanged();
            }
        }

        public Permissions Permissions { get; set; }

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
            foreach (Project project in _projects)
            {
                _projectMenu = new MenuItem
                {
                    Header = project.Name,
                    Command = goToCommand,
                    CommandParameter = project
                };

                if (SelectedProject.Id == project.Id)
                {
                    _projectMenu.Background = Brushes.Wheat;
                    _projectMenu.IsChecked = true;
                }

                projectMenuItems.Items.Add(_projectMenu);
            }
        }

        public void UpdateProject()
        {
            try
            {
                SelectedProject = _client.UpdateProject(SelectedProject);
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

        public ICommand GoToCreateSprintCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createSprint = new CreateSprint(SelectedProject);

                    NavigationService.GetNavigationService(this).Navigate(createSprint);
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
                    SelectedProject.ScrumMasters.Add((User)param);
                    UpdateProject();
                });
            }
        }

        public ICommand RemoveScrumMasterCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    SelectedProject.ScrumMasters.Remove((User)param);
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

        #region Binding

        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;

        #endregion
    }
}
