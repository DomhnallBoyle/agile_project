using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for CreateSprint.xaml
    /// </summary>
    public partial class CreateSprint : Page
    {
        #region Private Variables

        private Project _currentProject;

        private SprintClient _client;

        private DateTime _startDate;
        private DateTime _endDate;
        private String _warningMessage;
        private String _sprintName;

        #endregion

        #region Public Variables
        public string UserLabel { get; set; }
        public string image { get; set; }
        public string CurrentPage { get; set; }
        #endregion

        public CreateSprint(Project project)
        {
            InitializeComponent();
            generateHeader();
            DataContext = this;

            _client = new SprintClient();
            _currentProject = project;
        }

        #region Command Methods

        public ICommand CancelCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page manageSprintsPage = new ManageSprints(_currentProject);

                    NavigationService.GetNavigationService(this).Navigate(manageSprintsPage);
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

        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (CheckFields())
                    {
                        Sprint sprint = new Sprint(
                                _sprintName,
                                _startDate,
                                _endDate,
                                _currentProject,
                                (User)Application.Current.Properties["user"]);

                        try
                        {
                            _client.CreateSprint(sprint);
                            
                            MessageBoxUtil.ShowSuccessBox("Sprint Creation Successful!");

                            Page manageSprintsPage = new ManageSprints(_currentProject);

                            NavigationService.GetNavigationService(this).Navigate(manageSprintsPage);
                        }
                        catch (RestResponseErrorException ex)
                        {
                            MessageBoxUtil.ShowErrorBox(ex.Message);
                        }
                    } else
                    {
                        MessageBoxUtil.ShowWarningBox(_warningMessage);
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

        #endregion

        #region Class Methods

        private bool CheckFields()
        {
            bool valid = true;
            StringBuilder sb = new StringBuilder();
            if (!String.IsNullOrEmpty(SprintNameTextBox.Text))
            {
                _sprintName = SprintNameTextBox.Text;
            }
            else
            {
                valid = false;
                sb.Append("You must select a sprint name\n");
            }

            if (StartDatePicker.SelectedDate != null)
            {
                _startDate = (DateTime)StartDatePicker.SelectedDate;
            }
            else
            {
                valid = false;
                sb.Append("You must select a start date\n");
            }
            if (EndDatePicker.SelectedDate != null)
            {
                _endDate = (DateTime)EndDatePicker.SelectedDate;
            }
            else
            {
                valid = false;
                sb.Append("You must select an end date\n");
            }

            _warningMessage = sb.ToString();
            return valid;
        }

        #endregion
    }
}
