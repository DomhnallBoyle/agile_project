using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System.Collections.Generic;
using System.Linq;
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
    public partial class AddProjectTeamMember : Page
    {
        #region Private Variables

        private List<User> _searchResultUsers = new List<User>();
        private List<User> _addedTeamMembers = new List<User>();
        private List<User> _currentTeamMembers = new List<User>();
        private UserClient _userClient;
        private ProjectClient _projectClient;
        private Project _currentProject;

        #endregion

        public AddProjectTeamMember(Project project)
        {
            InitializeComponent();
            DataContext = this;
            _userClient = new UserClient();
            _projectClient = new ProjectClient();
            _currentProject = project;
            _currentTeamMembers = _projectClient.GetProjectTeam(project.Id);
        }

        #region Class methods

        public void DisplayResults(List<User> users)
        {
            ResultsHeader.Visibility = Visibility.Visible;
            for (int i = 0; i < users.Count; i++)
            {
                AddUserToResultDisplay(users[i], i);
            }
            SaveButton.Visibility = Visibility.Visible;
        }

        public void AddUserToResultDisplay(User user, int userNumber)
        {
            nameLabel.Visibility = Visibility.Visible;
            emailLabel.Visibility = Visibility.Visible;
            TextBlock resultName = new TextBlock();
            resultName.Text = user.Forename + " " + user.Surname;
            resultName.Height = 20;
            namePanel.Children.Add(resultName);
            TextBlock resultEmail = new TextBlock();
            resultEmail.Text = user.Email;
            resultEmail.Height = 20;
            emailPanel.Children.Add(resultEmail);
            Button addBtn = new Button();
            addBtn.Height = 20;
            addBtn.Name = "addBtn" + userNumber;
            addBtn.CommandParameter = userNumber;
            addButtonPanel.RegisterName(addBtn.Name, addBtn);
            if (_currentTeamMembers.Any(member => member.Id == user.Id))
            {
                addBtn.Content = "On Team";
                addBtn.Background = Brushes.DarkSalmon;
            }
            else
            {
                addBtn.Content = "Add";
                addBtn.Command = AddCommand;
            }

            addButtonPanel.Children.Add(addBtn);
        }

        public void DisplayNoResults()
        {
            noResultsMessage.Visibility = Visibility.Visible;
        }

        public void ResetResultsDisplay()
        {
            for (int i = 0; i < addButtonPanel.Children.Count; i++)
            {
                namePanel.UnregisterName(((Button)addButtonPanel.Children[i]).Name);
            }
            namePanel.Children.Clear();
            emailPanel.Children.Clear();
            addButtonPanel.Children.Clear();
            noResultsMessage.Visibility = Visibility.Hidden;
            ResultsHeader.Visibility = Visibility.Hidden;
            SaveButton.Visibility = Visibility.Hidden;
        }

        public User BuildSearchUser()
        {
            User user = new User();
            user.Roles = new Roles();
            user.Forename = FirstnameTextBox.Text;
            user.Surname = SurnameTextBox.Text;
            user.Email = EmailTextBox.Text;
            user.Roles.ProductOwner = ProductOwnerCheckBox.IsChecked.Value;
            user.Roles.Developer = DeveloperCheckBox.IsChecked.Value;
            user.Roles.ScrumMaster = ScrumMasterCheckBox.IsChecked.Value;
            return user;
        }

        #endregion

        #region Command methods
        public ICommand AddCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {

                        var userNumber = ((int)param);
                        if (!_addedTeamMembers.Contains((_searchResultUsers[userNumber])))
                        {
                            _addedTeamMembers.Add(_searchResultUsers[userNumber]);
                            Button buttonVar = ((Button)addButtonPanel.FindName("addBtn" + userNumber));
                            buttonVar.Command = null;
                            buttonVar.Content = "On Team";
                            buttonVar.Background = Brushes.DarkSalmon;
                        }
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
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
                        _projectClient.Add(_addedTeamMembers, _currentProject);
                        List<Project> projects = new List<Project>();
                        projects.Add(_currentProject);
                        Page projectDashboard = new ProjectDashboard(_currentProject);
                        NavigationService.GetNavigationService(this).Navigate(projectDashboard);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
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
                        ResetResultsDisplay();
                        User searchUser = BuildSearchUser();
                        _searchResultUsers = _userClient.Search(searchUser);
                        if (_searchResultUsers.Count != 0)
                        {
                            DisplayResults(_searchResultUsers);
                            _addedTeamMembers.Clear();
                        }
                        else
                        {
                            DisplayNoResults();
                        }
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