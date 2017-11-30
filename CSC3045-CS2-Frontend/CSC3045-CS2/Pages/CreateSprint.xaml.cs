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
    public partial class CreateSprint : BasePage
    {
        #region Private Variables

        private Project _currentProject;

        private SprintClient _client;

        private DateTime _startDate;
        private DateTime _endDate;

        private Style _invalidTextBoxStyle;
        private Style _validTextBoxStyle;

        private String _warningMessage;
        private String _sprintName;

        #endregion

        public CreateSprint(Project project)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _client = new SprintClient();
            _currentProject = project;

            pageSetup();
        }

        #region Class Methods

        private void pageSetup()
        {
            _invalidTextBoxStyle = FindResource("InvalidTextBox") as Style;
            _validTextBoxStyle = FindResource("DefaultTextBox") as Style;
            
        }

        private bool CheckFields()
        {
            bool valid = true;
            StringBuilder sb = new StringBuilder();

            if (!String.IsNullOrEmpty(SprintNameTextBox.Text))
            {
                SprintNameTextBox.Style = _validTextBoxStyle;
                _sprintName = SprintNameTextBox.Text;
            }
            else
            {
                SprintNameTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a Sprint Name\n");
            }

            if (StartDatePicker.SelectedDate != null)
            {
                _startDate = (DateTime)StartDatePicker.SelectedDate;
            }
            else
            {
                valid = false;
                sb.Append("You must select a Start Date\n");
            }

            if (EndDatePicker.SelectedDate != null)
            {
                _endDate = (DateTime)EndDatePicker.SelectedDate;
            }
            else
            {
                valid = false;
                sb.Append("You must select an End Date\n");
            }

            if (StartDatePicker.SelectedDate >= EndDatePicker.SelectedDate)
            {
                valid = false;
                sb.Append("The Sprint End Date must be after the Sprint Start Date");
            }

            if (StartDatePicker.SelectedDate < DateTime.Now)
            {
                valid = false;
                sb.Append("The Sprint Start Date can't be in the past");
            }

            _warningMessage = sb.ToString();
            return valid;
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

        #endregion
    }
}
