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
    /// Interaction logic for AddProjectTeamMember.xaml
    /// </summary>
    public partial class AddProjectTeamMember : Page, INotifyPropertyChanged
    {
        #region Private Variables

        private ObservableCollection<User> _currentTeamMembers = new ObservableCollection<User>();

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
            get { return _currentTeamMembers; }
        }

        #endregion

        public AddProjectTeamMember(Project project)
        {
            InitializeComponent();
            DataContext = this;

            _userClient = new UserClient();
            _projectClient = new ProjectClient();

            _currentProject = project;
            _currentTeamMembers = new ObservableCollection<User>(_projectClient.GetProjectTeam(project.Id));
        }

        #region Command methods
        public ICommand SearchCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        User searchUser = new User("", "", EmailTextBox.Text, new Roles(false, false, false));
                        SearchResultUser = _userClient.Search(searchUser);
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
                    if (!TeamMembers.Contains(SearchResultUser))
                    {
                        TeamMembers.Add(SearchResultUser);
                    }
                });
            }
        }

        public ICommand CancelCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page projectDashboardPage = new ProjectDashboard(_currentProject);

                    NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
                });
            }
        }

        public ICommand SaveCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        List<User> teamMembers = new List<User>(TeamMembers);
                        _projectClient.Add(teamMembers, _currentProject);

                        Page projectDashboardPage = new ProjectDashboard(_currentProject);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
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