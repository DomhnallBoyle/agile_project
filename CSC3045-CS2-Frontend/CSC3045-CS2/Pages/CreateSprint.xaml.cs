using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
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

        #endregion

        public CreateSprint(Project project)
        {
            InitializeComponent();
            DataContext = this;

            _client = new SprintClient();
            _currentProject = project;
        }

        #region Class Methods

        private bool CheckFields()
        {
            if (StartDatePicker.SelectedDate != null)
            {
                _startDate = (DateTime)StartDatePicker.SelectedDate;
                if (EndDatePicker.SelectedDate != null)
                {
                    _endDate = (DateTime)EndDatePicker.SelectedDate;
                    return true;
                }
            }
            return false;
        }

        #endregion

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

        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (CheckFields())
                    {
                        Sprint sprint = new Sprint(
                                "Sprint",
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
                        MessageBoxUtil.ShowWarningBox("Please select a Start and End Date for the Sprint");
                    }
                });
            }
        }

        #endregion
    }
}
