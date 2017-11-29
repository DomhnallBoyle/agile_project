using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
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
            _teamMembers = new SprintClient().GetSprintTeam(_sprintId);
            _client = new TaskClient();
            GenerateTeamMembers();
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
        /// 
        /// </summary>
        /// <returns></returns>
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

            if (AssigneesComboBox.SelectedIndex != -1)
            {
                _taskAssignee = _teamMembers[AssigneesComboBox.SelectedIndex];
                valid = true;
            }
            else
            {
                _taskAssignee = null;
                Console.WriteLine(AssigneesComboBox.SelectedIndex);
                valid = true;
            }

            _warningMessage = sb.ToString();
            return valid;
        }

        /// <summary>
        /// 
        /// </summary>
        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (CheckFields())
                    {
                        Console.WriteLine(_taskAssignee);
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
    }
}
