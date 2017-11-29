using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for AddTask.xaml
    /// </summary>
    public partial class ManageTasks : BasePage
    {
        # region private variables

        private UserStory _taskUserStory { get; set; }
        private long _sprintId { get; set; }
        private List<User> _teamMembers { get; set; }
        private TaskClient _client { get; set; }
        private String _warningMessage { get; set; }

        private String _taskName { get; set; }
        private String _taskDescription { get; set; }
        private int _taskInitialEstimate { get; set; }
        private User _taskAssignee { get; set; }

        # endregion 

        public ManageTasks(UserStory userStory, long sprintId)
        {
            InitializeComponent();
            DataContext = this;
            CurrentPage = this.Title;
            this._taskUserStory = userStory; 
            this._sprintId = sprintId;
            _teamMembers = new SprintClient().GetSprintTeam(userStory.Project.Id, _sprintId);
            _client = new TaskClient();
            GenerateTeamMembers();
        }

        #region private methods

        /// <summary>
        /// Performs Regex Validation live on the TextBox's as data is entered
        /// Ensures that only digits can be entered into the TextBox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void NumberOnlyTextBoxValidation(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        /// <summary>
        /// Generates the combobox with a list of team members from the sprint
        /// </summary>
        private void GenerateTeamMembers()
        {
            List<String> MemberNames = new List<String>();
            for (int i = 0; i < _teamMembers.Count; i++)
            {
                AssigneesComboBox.Items.Add(_teamMembers[i].GetFullName());
            }
        }

        /// <summary>
        /// Checks input fields are valid before sending create task request
        /// </summary>
        /// <returns>True/False depending on validity of input fields</returns>
        private bool CheckFields()
        {
            bool valid = true;
            StringBuilder sb = new StringBuilder();

            if (!String.IsNullOrEmpty(NameTextBox.Text))
            {
                NameTextBox.Style = (Style)FindResource("DefaultTextBox");
                NameTextBlock.Style = (Style)FindResource("Watermark");
                _taskName = NameTextBox.Text;
            }
            else
            {
                NameTextBox.Style = (Style)FindResource("InvalidTextBox");
                NameTextBlock.Style = (Style)FindResource("InvalidWatermark");
                valid = false;
                sb.Append("You must enter a Task Name\n");
            }

            if (!String.IsNullOrEmpty(DescriptionTextBox.Text))
            {
                DescriptionTextBox.Style = (Style)FindResource("DefaultTextBox");
                DescriptionTextBlock.Style = (Style)FindResource("Watermark");
                _taskDescription = DescriptionTextBox.Text;
            }
            else
            {
                DescriptionTextBox.Style = (Style)FindResource("InvalidTextBox");
                DescriptionTextBlock.Style = (Style)FindResource("InvalidWatermark");
                valid = false;
                sb.Append("You must enter a Description Name\n");
            }
            if (!String.IsNullOrEmpty(InitialEstimateTextBox.Text))
            {
                InitialEstimateTextBox.Style = (Style)FindResource("DefaultTextBox");
                InitialEstimateTextBlock.Style = (Style)FindResource("Watermark");
                _taskInitialEstimate = int.Parse(InitialEstimateTextBox.Text);
            }
            else
            {
                InitialEstimateTextBox.Style = (Style)FindResource("InvalidTextBox");
                InitialEstimateTextBlock.Style = (Style)FindResource("InvalidWatermark");
                valid = false;
            }
            if (AssigneesComboBox.SelectedIndex != -1)
            {
                _taskAssignee = _teamMembers[AssigneesComboBox.SelectedIndex];
                valid = true;
            }
            else
            {
                _taskAssignee = null;
                valid = true;
            }

            _warningMessage = sb.ToString();
            return valid;
        }

        #endregion

        #region public command methods
        
        /// <summary>
        /// Create command button - sends create task request
        /// </summary>
        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (CheckFields())
                    {
                        Task task = new Task(
                            _taskName,
                            _taskDescription,
                            _taskInitialEstimate,
                            _taskUserStory,
                            _taskAssignee
                        );

                        try
                        {
                            _client.CreateTask(task, _taskUserStory.Project.Id, _taskUserStory.Id);

                            MessageBoxUtil.ShowSuccessBox("Task Creation Successful!");
                        }
                        catch (RestResponseErrorException ex)
                        {
                         
                            MessageBoxUtil.ShowErrorBox(ex.Message);
                        }
                    }
                    else
                    {
                        MessageBoxUtil.ShowErrorBox(_warningMessage);
                    }
                });
            }
        }
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page manageSprintsPage = new SprintDashboard(_taskUserStory.Sprint, true);

                    NavigationService.GetNavigationService(this).Navigate(manageSprintsPage);

                });
            }
        }

        #endregion 
    }
}
