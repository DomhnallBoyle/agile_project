using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;

using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
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
    public partial class ProjectDashboard : BasePage, INotifyPropertyChanged
    {
        #region Private Variables

        private ObservableCollection<User> _teamMembers = new ObservableCollection<User>();

        private List<Project> _projects;
        private MenuItem _projectMenu;

        private Project _currentProject;
        private ProjectClient _projectClient;

        private User _searchResultUser;
        private UserClient _userClient;

        #endregion

        #region Public Variables

        public User SearchResultUser
        {
            get { return _searchResultUser; }
            set
            {
                _searchResultUser = value;
                OnPropertyChanged();
            }
        }

        public ObservableCollection<User> TeamMembers
        {
            get { return _teamMembers; }
            set
            {
                _teamMembers = value;
                OnPropertyChanged();
            }
        }

        public Project CurrentProject
        {
            get { return _currentProject; }
            set
            {
                _currentProject = value;
                OnPropertyChanged();
            }
        }

        public Permissions Permissions { get; set; }

        public bool IsCurrentProductOwner
        {
            get
            {
                User selectedUser = (User)TeamMembersListBox.SelectedItem;
                
                return CurrentProject.ProductOwner == null ? false : selectedUser.Id == CurrentProject.ProductOwner.Id;
            }
        }

        public bool IsCurrentScrumMaster
        {
            get
            {
                User selectedUser = (User)TeamMembersListBox.SelectedItem;

                return CurrentProject.ScrumMasters.Find(user => user.Id == selectedUser.Id) != null;
            }
        }

        #endregion

        public ProjectDashboard(Project currentProject)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _projectClient = new ProjectClient();
            _userClient = new UserClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], currentProject);

            CurrentProject = currentProject;
            _projects = _userClient.GetProjectsForUser(((User)Application.Current.Properties["user"]).Id);
            ProjectDropDownButton.Content = currentProject.Name;
            AddProjectsToDropdownList();

            try
            {
                TeamMembers = new ObservableCollection<User>(_projectClient.GetProjectTeam(currentProject.Id));
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

        #region Class methods

        public void UpdateProject()
        {
            try
            {
                CurrentProject = _projectClient.UpdateProject(CurrentProject);
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

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

                if (CurrentProject.Id == project.Id)
                {
                    _projectMenu.Background = Brushes.Wheat;
                    _projectMenu.IsChecked = true;
                }

                projectMenuItems.Items.Add(_projectMenu);
            }
        }

        private void UpdateSearchUI()
        {
            if (TeamMembers.Any(user => user.Id == SearchResultUser.Id))
            {
                switchButtonState(AddToTeamButton, false);
            }
            else
            {
                switchButtonState(AddToTeamButton, true);
            }
        }

        private void switchButtonState(Button button, bool enabled)
        {
            if (enabled)
            {
                button.Style = (Style)FindResource("StandardButton");
                button.IsEnabled = true;
                button.Content = "Add";
            }
            else
            {
                button.Style = (Style)FindResource("InvalidButton");
                button.IsEnabled = false;
                button.Content = "Added";
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
                    Page productBacklog = new ProductBacklog(CurrentProject);

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

        public ICommand GoToManageSprintsCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page manageSprints = new ManageSprints(CurrentProject);

                    NavigationService.GetNavigationService(this).Navigate(manageSprints);
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
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        public ICommand SearchCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        if (!string.IsNullOrEmpty(SearchEmailTextBox.Text))
                        {
                            User searchUser = new User("", "", SearchEmailTextBox.Text, new Roles(false, false, false));
                            SearchResultUser = _userClient.Search(searchUser);

                            UpdateSearchUI();
                        }
                        else
                        {
                            MessageBoxUtil.ShowWarningBox("Please enter a user email to search for");
                        }
                    }
                    catch (RestResponseErrorException ex)
                    {
                        SearchResultUser = null;
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        public ICommand AddToTeamCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (!TeamMembers.Any(user => user.Id == SearchResultUser.Id))
                    {
                        try
                        {
                            TeamMembers.Add(SearchResultUser);
                            List<User> teamMembers = new List<User>(TeamMembers);
                            _projectClient.Add(teamMembers, _currentProject);

                            UpdateSearchUI();
                        }
                        catch (RestResponseErrorException ex)
                        {
                            MessageBoxUtil.ShowErrorBox(ex.Message);
                        }
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
                    CurrentProject.ScrumMasters.Add((User)param);
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
                    CurrentProject.ScrumMasters.Remove((User)param);
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
                    CurrentProject.ProductOwner = ((User)param);
                    UpdateProject();
                });
            }
        }

        #endregion

        #region Event methods

        private void TeamMembersListBox_ContextMenuOpening(object sender, ContextMenuEventArgs e)
        {
            var selectedItem = (User)TeamMembersListBox.SelectedItem;

            if ((!Permissions.Manager || (!selectedItem.Roles.ProductOwner && !selectedItem.Roles.ScrumMaster)) || selectedItem == null)
            {
                e.Handled = true;
            }
        }

        private void ScrumMastersListBox_ContextMenuOpening(object sender, ContextMenuEventArgs e)
        {
            var selectedItem = (User)ScrumMastersListBox.SelectedItem;

            if ((!Permissions.Manager || (!selectedItem.Roles.ProductOwner && !selectedItem.Roles.ScrumMaster)) || selectedItem == null)
            {
                e.Handled = true;
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
